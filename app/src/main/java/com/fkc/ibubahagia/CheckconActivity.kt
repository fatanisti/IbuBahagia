 package com.fkc.ibubahagia

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.PowerManager
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.fkc.ibubahagia.databinding.ActivityCheckconBinding

 class CheckconActivity : AppCompatActivity() {
     private lateinit var binding : ActivityCheckconBinding
     private lateinit var mHomeWatcher : HomeWatcher
     private var counter = 0
     private var sumScore = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkcon)

        doBindService()
        val music = Intent()
        music.setClass(this, MusicService::class.java)
        startService(music)

        mHomeWatcher = HomeWatcher(this)
        mHomeWatcher.setOnHomePressedListener(object: HomeWatcher.OnHomePressedListener {
            override fun onHomePressed() {
                if (mServ != null)
                {
                    mServ!!.pauseMusic()
                }
            }
            override fun onHomeLongPressed() {
                if (mServ != null)
                {
                    mServ!!.pauseMusic()
                }
            }
        })
        mHomeWatcher.startWatch()

        counter = 0
        sumScore = 0

        val questionArray = resources.getStringArray(R.array.questions_array)

        val btnMulai = binding.btnMulai
        btnMulai.setOnClickListener {
            binding.intro.visibility = View.INVISIBLE
            binding.form.visibility = View.VISIBLE
            binding.question.visibility = View.VISIBLE
            binding.answer2Group.visibility = View.GONE
            binding.answer3Group.visibility = View.VISIBLE
            binding.answer5Group.visibility = View.GONE
            binding.answer6Group.visibility = View.GONE
            binding.checkboxContainer.visibility = View.GONE
            binding.btnNext.visibility = View.VISIBLE
            binding.question.text = questionArray[counter]
            binding.answer31.text = resources.getString(R.string.konten_ck_a_0_1)
            binding.answer32.text = resources.getString(R.string.konten_ck_a_0_2)
            binding.answer33.text = resources.getString(R.string.konten_ck_a_0_3)
        }

        val btnNext = binding.btnNext
        btnNext.setOnClickListener {
            when (counter) {
                0-> {
                    sumScore += if (binding.answer31.isChecked){
                        0
                    } else {
                        4
                    }
                    binding.answer3Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer2Group.visibility = View.VISIBLE
                    binding.answer3Group.visibility = View.GONE
                    binding.answer21.text = resources.getString(R.string.konten_ck_a_1_1)
                    binding.answer22.text = resources.getString(R.string.konten_ck_a_1_2)
                }
                1 -> {
                    sumScore += if (binding.answer21.isChecked){
                        4
                    } else {
                        0
                    }
                    binding.answer2Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer5Group.visibility = View.VISIBLE
                    binding.answer2Group.visibility = View.GONE
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_2_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_2_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_2_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_2_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_2_5)
                }
                2 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_3_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_3_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_3_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_3_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_3_5)
                }
                3 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_4_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_4_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_4_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_4_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_4_5)
                }
                4 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_5_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_5_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_5_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_5_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_5_5)
                }
                5 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_6_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_6_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_6_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_6_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_6_5)
                }
                6 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_7_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_7_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_7_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_7_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_7_5)
                }
                7 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=5
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=4
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=2
                        }
                        else -> {
                            sumScore+=1
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_8_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_8_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_8_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_8_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_8_5)
                }
                8 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_9_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_9_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_9_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_9_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_9_5)
                }
                9 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer6Group.visibility = View.VISIBLE
                    binding.answer5Group.visibility = View.GONE
                    binding.answer61.text = resources.getString(R.string.konten_ck_a_10_1)
                    binding.answer62.text = resources.getString(R.string.konten_ck_a_10_2)
                    binding.answer63.text = resources.getString(R.string.konten_ck_a_10_3)
                    binding.answer64.text = resources.getString(R.string.konten_ck_a_10_4)
                    binding.answer65.text = resources.getString(R.string.konten_ck_a_10_5)
                    binding.answer66.text = resources.getString(R.string.konten_ck_a_10_6)
                }
                10 -> {
                    when {
                        binding.answer61.isChecked -> {
                            sumScore+=5
                        }
                        binding.answer62.isChecked -> {
                            sumScore+=4
                        }
                        binding.answer63.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer64.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer65.isChecked -> {
                            sumScore+=1
                        }
                        else -> {
                            sumScore+=4
                        }
                    }
                    binding.answer6Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer5Group.visibility = View.VISIBLE
                    binding.answer6Group.visibility = View.GONE
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_11_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_11_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_11_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_11_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_11_5)
                }
                11 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=0
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=1
                        }
                        else -> {
                            sumScore+=2
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_12_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_12_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_12_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_12_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_12_5)
                }
                12 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=5
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=4
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=2
                        }
                        else -> {
                            sumScore+=1
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_13_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_13_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_13_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_13_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_13_5)
                }
                13 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=5
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=4
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=2
                        }
                        else -> {
                            sumScore+=1
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_14_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_14_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_14_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_14_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_14_5)
                }
                14 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_15_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_15_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_15_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_15_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_15_5)
                }
                15 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer6Group.visibility = View.VISIBLE
                    binding.answer5Group.visibility = View.GONE
                    binding.answer61.text = resources.getString(R.string.konten_ck_a_16_1)
                    binding.answer62.text = resources.getString(R.string.konten_ck_a_16_2)
                    binding.answer63.text = resources.getString(R.string.konten_ck_a_16_3)
                    binding.answer64.text = resources.getString(R.string.konten_ck_a_16_4)
                    binding.answer65.text = resources.getString(R.string.konten_ck_a_16_5)
                    binding.answer66.text = resources.getString(R.string.konten_ck_a_16_6)
                }
                16 -> {
                    when {
                        binding.answer61.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer62.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer63.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer64.isChecked -> {
                            sumScore+=4
                        }
                        binding.answer65.isChecked -> {
                            sumScore+=5
                        }
                        else -> {
                            sumScore+=3
                        }
                    }
                    binding.answer6Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer61.text = resources.getString(R.string.konten_ck_a_17_1)
                    binding.answer62.text = resources.getString(R.string.konten_ck_a_17_2)
                    binding.answer63.text = resources.getString(R.string.konten_ck_a_17_3)
                    binding.answer64.text = resources.getString(R.string.konten_ck_a_17_4)
                    binding.answer65.text = resources.getString(R.string.konten_ck_a_17_5)
                    binding.answer66.text = resources.getString(R.string.konten_ck_a_17_6)
                }
                17 -> {
                    when {
                        binding.answer61.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer62.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer63.isChecked -> {
                            sumScore+=0
                        }
                        binding.answer64.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer65.isChecked -> {
                            sumScore+=2
                        }
                        else -> {
                            sumScore+=3
                        }
                    }
                    binding.answer6Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer61.text = resources.getString(R.string.konten_ck_a_18_1)
                    binding.answer62.text = resources.getString(R.string.konten_ck_a_18_2)
                    binding.answer63.text = resources.getString(R.string.konten_ck_a_18_3)
                    binding.answer64.text = resources.getString(R.string.konten_ck_a_18_4)
                    binding.answer65.text = resources.getString(R.string.konten_ck_a_18_5)
                    binding.answer66.text = resources.getString(R.string.konten_ck_a_18_6)
                }
                18 -> {
                    when {
                        binding.answer61.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer62.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer63.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer64.isChecked -> {
                            sumScore+=4
                        }
                        binding.answer65.isChecked -> {
                            sumScore+=5
                        }
                        else -> {
                            sumScore+=6
                        }
                    }
                    binding.answer6Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer61.text = resources.getString(R.string.konten_ck_a_19_1)
                    binding.answer62.text = resources.getString(R.string.konten_ck_a_19_2)
                    binding.answer63.text = resources.getString(R.string.konten_ck_a_19_3)
                    binding.answer64.text = resources.getString(R.string.konten_ck_a_19_4)
                    binding.answer65.text = resources.getString(R.string.konten_ck_a_19_5)
                    binding.answer66.text = resources.getString(R.string.konten_ck_a_19_6)
                }
                19 -> {
                    when {
                        binding.answer61.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer62.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer63.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer64.isChecked -> {
                            sumScore+=4
                        }
                        binding.answer65.isChecked -> {
                            sumScore+=5
                        }
                        else -> {
                            sumScore+=6
                        }
                    }
                    binding.answer6Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer5Group.visibility = View.VISIBLE
                    binding.answer6Group.visibility = View.GONE
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_20_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_20_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_20_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_20_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_20_5)
                }
                20 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_21_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_21_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_21_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_21_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_21_5)
                }
                21 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.answer51.text = resources.getString(R.string.konten_ck_a_22_1)
                    binding.answer52.text = resources.getString(R.string.konten_ck_a_22_2)
                    binding.answer53.text = resources.getString(R.string.konten_ck_a_22_3)
                    binding.answer54.text = resources.getString(R.string.konten_ck_a_22_4)
                    binding.answer55.text = resources.getString(R.string.konten_ck_a_22_5)
                }
                22 -> {
                    when {
                        binding.answer51.isChecked -> {
                            sumScore+=1
                        }
                        binding.answer52.isChecked -> {
                            sumScore+=2
                        }
                        binding.answer53.isChecked -> {
                            sumScore+=3
                        }
                        binding.answer54.isChecked -> {
                            sumScore+=4
                        }
                        else -> {
                            sumScore+=5
                        }
                    }
                    binding.answer5Group.clearCheck()
                    counter++
                    binding.question.text = questionArray[counter]
                    binding.checkboxContainer.visibility = View.VISIBLE
                    binding.answer5Group.visibility = View.GONE
                    binding.checkbox1.text = resources.getString(R.string.konten_ck_a_23_1)
                    binding.checkbox2.text = resources.getString(R.string.konten_ck_a_23_2)
                    binding.checkbox3.text = resources.getString(R.string.konten_ck_a_23_3)
                    binding.checkbox4.text = resources.getString(R.string.konten_ck_a_23_4)
                    binding.checkbox5.text = resources.getString(R.string.konten_ck_a_23_5)
                    binding.checkbox6.text = resources.getString(R.string.konten_ck_a_23_6)
                }
                23 -> {
                    if (binding.checkbox1.isChecked){
                        val temp1 = -1
                        sumScore += temp1
                        binding.checkbox1.toggle()
                    }
                    if (binding.checkbox2.isChecked){
                        val temp2 = -1
                        sumScore += temp2
                        binding.checkbox2.toggle()
                    }
                    if (binding.checkbox3.isChecked){
                        val temp3 = -1
                        sumScore += temp3
                        binding.checkbox3.toggle()
                    }
                    if (binding.checkbox4.isChecked){
                        val temp4 = -1
                        sumScore += temp4
                        binding.checkbox4.toggle()
                    }
                    if (binding.checkbox5.isChecked){
                        val temp5 = -1
                        sumScore += temp5
                        binding.checkbox5.toggle()
                    }
                    if (binding.checkbox6.isChecked){
                        val temp6 = 0
                        sumScore += temp6
                        binding.checkbox6.toggle()
                    }
                    val skor = resources.getString(R.string.konten_ck_skor)
                    val baik = resources.getString(R.string.konten_ck_baik)
                    val buruk = resources.getString(R.string.konten_ck_buruk)
                    binding.checkboxContainer.visibility = View.GONE
                    binding.title.text = resources.getString(R.string.konten_ck_hasil)
                    binding.form.visibility = View.INVISIBLE
                    binding.outro.visibility = View.VISIBLE
                    binding.skor.text = "$skor $sumScore"
                    if (sumScore <= 45){
                        binding.penjelasan.text = baik
                    }
                    else {
                        binding.penjelasan.text = buruk
                    }
                }
            }
        }

        val btnHome = binding.btnHome
        btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

    }
    override fun onBackPressed() {
        if (counter == 23 || counter == 0) {
            super.onBackPressed()
        }
        else {
            // build alert dialog
            val dialogBuilder = AlertDialog.Builder(this)

            // set message of alert dialog
            dialogBuilder.setMessage("Apakah anda ingin berhenti mengisi form deteksi?")
                    // if the dialog is cancelable
                    .setCancelable(false)
                    // positive button text and action
                    .setPositiveButton("Ya") { _, _ -> finish()
                    }
                    // negative button text and action
                    .setNegativeButton("Tidak") { dialog, _ -> dialog.cancel()
                    }

            // create dialog box
            val alert = dialogBuilder.create()
            // set title for alert dialog box
            alert.setTitle("KONFIRMASI BERHENTI")
            // show alert dialog
            alert.show()
        }
    }
     private var mIsBound = false
     private var mServ: MusicService? = null
     private val serviceConnection: ServiceConnection = object : ServiceConnection {
         override fun onServiceConnected(name: ComponentName, binder: IBinder) {
             mServ = (binder as MusicService.ServiceBinder).service
         }
         override fun onServiceDisconnected(name: ComponentName) {
             mServ = null
         }
     }

     private fun doBindService() {
         bindService(
                 Intent(this, MusicService::class.java),
                 serviceConnection, Context.BIND_AUTO_CREATE
         )
         mIsBound = true
     }

     private fun doUnbindService() {
         if (mIsBound) {
             unbindService(serviceConnection)
             mIsBound = false
         }
     }

     override fun onResume() {
         super.onResume()
         if (mServ != null) {
             mServ!!.resumeMusic()
         }
     }

     override fun onPause() {
         super.onPause()
         val pm =
                 getSystemService(Context.POWER_SERVICE) as PowerManager
         val isScreenOn: Boolean
         isScreenOn = pm.isInteractive
         if (!isScreenOn) {
             if (mServ != null) {
                 mServ!!.pauseMusic()
             }
         }
     }

     override fun onDestroy() {
         super.onDestroy()
         doUnbindService()
         mHomeWatcher.stopWatch()
         val music = Intent()
         music.setClass(this, MusicService::class.java)
         stopService(music)
     }
}